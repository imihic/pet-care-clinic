import { useEffect, useState } from 'react';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Grid } from '@vaadin/react-components/Grid.js';
import { GridColumn } from '@vaadin/react-components/GridColumn.js';
import { ApplicationEndpoint } from 'Frontend/generated/endpoints';
import ApplicationDTO from "Frontend/generated/hr/tvz/application/dto/ApplicationDTO";
import { Button } from '@vaadin/react-components/Button.js';
import { UserEndpoint } from "Frontend/generated/endpoints";

export const config: ViewConfig = {
    menu: { order: 3, icon: 'line-awesome/svg/envelope-solid.svg' },
    title: 'Applications',
    loginRequired: true,
};

export default function ApplicationsView() {
    const [applications, setApplications] = useState<ApplicationDTO[]>([]);
    const [isAdmin, setIsAdmin] = useState<boolean>(false);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchIsAdmin = async () => {
            try {
                const adminStatus = await UserEndpoint.isAdmin();
                setIsAdmin(adminStatus);
            } catch {
                setIsAdmin(false);
            }
        };

        const fetchApplications = async () => {
            setIsLoading(true);
            if (isAdmin) {
                const applications = await ApplicationEndpoint.getAllApplications();
                if (applications) {
                    setApplications(applications.filter(app => app !== undefined) as ApplicationDTO[]);
                }
            } else {
                const userId = 1; // Replace with actual user ID
                const applications = await ApplicationEndpoint.getApplicationsByUser(userId);
                if (applications) {
                    setApplications(applications.filter(app => app !== undefined) as ApplicationDTO[]);
                }
            }
            setIsLoading(false);
        };

        fetchIsAdmin().then(fetchApplications);
    }, [isAdmin]);

    const approveApplication = async (id: number) => {
        await ApplicationEndpoint.approveApplication(id);
        setApplications(prevApplications => prevApplications.map(app => app.id === id ? { ...app, status: 'APPROVED' } : app));
    };

    const rejectApplication = async (id: number) => {
        await ApplicationEndpoint.rejectApplication(id);
        setApplications(prevApplications => prevApplications.map(app => app.id === id ? { ...app, status: 'REJECTED' } : app));
    };

    const actionRenderer = ({ item }) => (
        <>
            <Button theme="success primary small" onClick={() => approveApplication(item.id)}>Approve</Button> &nbsp;
            <Button theme="error primary small" onClick={() => rejectApplication(item.id)}>Reject</Button>
        </>
    );

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <section>
                <Grid items={applications}>
                    <GridColumn path="id" header="Application ID" autoWidth />
                    <GridColumn path="status" header="Status" autoWidth />
                    <GridColumn path="submissionDate" header="Submission Date" autoWidth />
                    <GridColumn path="notes" header="Notes" autoWidth />
                    {isAdmin && (
                        <GridColumn header="Actions" autoWidth>
                            {actionRenderer}
                        </GridColumn>
                    )}
                </Grid>
            </section>
        </>
    );
}
