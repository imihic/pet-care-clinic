import { useEffect, useState } from 'react';
import { Grid } from '@vaadin/react-components/Grid';
import { GridColumn } from '@vaadin/react-components/GridColumn';
import { getAllPets } from '../generated/PetEndpoint.js';
import { Avatar } from '@vaadin/react-components/Avatar';
import { HorizontalLayout } from '@vaadin/react-components/HorizontalLayout';
import { VerticalLayout } from '@vaadin/react-components/VerticalLayout';
import PetDTO from '../generated/hr/tvz/application/dto/PetDTO';







export const config = {
    menu: { order: 2, icon: 'line-awesome/svg/database-solid.svg' },
    title: <span className="font-bold">Pets Database</span>,
    loginRequired: true,
};

const petRenderer = (pet: PetDTO) => {
    const imageUrl = pet.photo ? `data:image/jpeg;base64,${pet.photo}` : undefined;
    console.log('Pet:', pet);  // Log entire pet object
    console.log('Pet Image URL:', imageUrl);  // Debugging step

    return (
        <HorizontalLayout style={{ alignItems: 'center', justifyContent: 'start' }} theme="spacing" className="justify-start items-center">
            <Avatar img={imageUrl} name={pet.name} />
            <VerticalLayout style={{ lineHeight: 'var(--lumo-line-height-m)' }}>
                <span>{pet.name}</span>
            </VerticalLayout>
        </HorizontalLayout>
    );
};

const vaccinatedRenderer = (pet: PetDTO) => (
    <span theme={`badge ${pet.vaccinated ? 'success' : 'error'}`}>
        {pet.vaccinated ? 'Vaccinated' : 'Not Vaccinated'}
    </span>
);

export default function PetsDatabaseView() {
    const [pets, setPets] = useState<PetDTO[]>([]);

    useEffect(() => {
        async function fetchPets() {
            const petsList = await getAllPets();
            if (petsList) {
                console.log('Fetched Pets:', petsList);  // Debugging step
                setPets(petsList.filter(pet => pet !== undefined));
            }
        }

        fetchPets();
    }, []);

    return (
        <section>
            <Grid items={pets} style={{ '--vaadin-grid-header-text-align': 'center' }}>
                <GridColumn header="Pet" autoWidth>
                    {({ item }) => petRenderer(item)}
                </GridColumn>
                <GridColumn path="age" header="Age" autoWidth />
                <GridColumn path="birthDate" header="Birth Date" autoWidth />
                <GridColumn header="Description" autoWidth>
                    {({ item }) => <span>{item.description}</span>}
                </GridColumn>
                <GridColumn header="Vaccination Status" autoWidth>
                    {({ item }) => vaccinatedRenderer(item)}
                </GridColumn>
            </Grid>
        </section>
    );
}
