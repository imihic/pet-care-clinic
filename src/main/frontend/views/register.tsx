import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import * as React from "react";
import { useState } from "react";
import { useForm } from "@vaadin/hilla-react-form";
import RegisterUserDTO from 'Frontend/generated/hr/tvz/application/dto/RegisterUserDTO';
import RegisterUserDTOModel from "Frontend/generated/hr/tvz/application/dto/RegisterUserDTOModel";
import { useNavigate } from 'react-router-dom';
import { Button } from '@vaadin/react-components/Button';
import { DatePicker } from '@vaadin/react-components/DatePicker';
import { FormLayout } from '@vaadin/react-components/FormLayout';
import { NumberField } from '@vaadin/react-components/NumberField';
import { PasswordField } from '@vaadin/react-components/PasswordField';
import { RadioButton } from '@vaadin/react-components/RadioButton';
import { RadioGroup } from '@vaadin/react-components/RadioGroup';
import { Select } from '@vaadin/react-components/Select';
import { TextField } from '@vaadin/react-components/TextField';
import { Upload } from '@vaadin/react-components/Upload';

export const config: ViewConfig = {
    menu: { exclude: true },
    title: 'Register',
    loginRequired: false,
};

export default function RegisterView() {
    const { model, field, read, submit } = useForm(RegisterUserDTOModel, {
        onSubmit: async (data: RegisterUserDTO) => {
            console.log({ ...data, profilePicture });
        }
    });

    const [profilePicture, setProfilePicture] = useState<string | ArrayBuffer | null>(null);

    const handleUpload = (event: CustomEvent) => {
        const file = event.detail.files[0];
        const reader = new FileReader();
        reader.onload = () => {
            setProfilePicture(reader.result);
        };
        reader.readAsDataURL(file);
    };

    const items = [
        {
            label: 'Bulldog',
            value: 'bulldog',
        },
        {
            label: 'Poodle',
            value: 'poodle',
        },
        {
            label: 'Jack Russel Terrier',
            value: 'jackrusselterrier',
        },
        {
            label: 'Golden Retriever',
            value: 'goldenretriever',
        },
        {
            label: 'Labrador Retriever',
            value: 'labradorretriever',
        },
    ];

    const navigate = useNavigate();

    const handleCancel = () => {
        navigate('/login');
    };


    return (
        <div className="registration-form-container">
            <h1 style={{ textAlign: 'center' }}>Pet Care Clinic Registration</h1>
            <form onSubmit={submit}>
                <FormLayout>
                    <TextField
                        label="Username"
                        value={model.username}
                        onChange={e => model.username = e.target.value}
                    />
                    <PasswordField
                        label="Password"
                        value={model.password}
                        onChange={e => model.password = e.target.value}
                    />
                    <TextField
                        label="First Name"
                        value={model.firstName}
                        onChange={e => model.firstName = e.target.value}
                    />
                    <TextField
                        label="Last Name"
                        value={model.lastName}
                        onChange={e => model.lastName = e.target.value}
                    />
                    <TextField
                        label="Street"
                        value={model.street}
                        onChange={e => model.street = e.target.value}
                    />
                    <TextField
                        label="ZIP"
                        value={model.zip}
                        onChange={e => model.zip = e.target.value}
                    />
                    <TextField
                        label="City"
                        value={model.city}
                        onChange={e => model.city = e.target.value}
                    />
                    <TextField
                        label="Country"
                        value={model.country}
                        onChange={e => model.country = e.target.value}
                    />
                    <Select
                        label="Breed Preferences"
                        value={model.breedPreferences}
                        onChange={e => model.breedPreferences = e.target.value}
                        items={items}
                    >
                    </Select>
                    <Upload
                        aria-label="Profile picture"
                        accept="image/jpg, image/jpeg, image/png"
                        maxFiles={3}
                        onUploadSuccess={handleUpload}
                    />
                    <NumberField
                        label="Budget"
                        value={model.budget}
                        onChange={e => model.budget = e.target.value}
                    />
                    <RadioGroup
                        label="Adoption status"
                        value={model.openToAdoptions}
                        onChange={e => model.openToAdoptions = e.target.value}
                    >
                        <RadioButton value="true" label="Open for adoptions"></RadioButton>
                        <RadioButton value="false" label="Not open for adoptions"></RadioButton>
                    </RadioGroup>
                    <DatePicker
                        label="From"
                        value={model.adoptionFromDate}
                        onChange={e => model.adoptionFromDate = e.target.value}
                    />
                    <DatePicker
                        label="To"
                        value={model.adoptionToDate}
                        onChange={e => model.adoptionToDate = e.target.value}
                    />
                    <Button theme="primary"  onClick={submit} className="half-width">Register</Button>
                    <Button theme="tertiary" className="half-width" onClick={handleCancel}>Cancel</Button>

                </FormLayout>
            </form>
        </div>
    );
}