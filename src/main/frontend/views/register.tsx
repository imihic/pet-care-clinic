import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import {
    Button,
    DatePicker,
    FormLayout,
    NumberField,
    PasswordField, RadioButton,
    RadioGroup,
    Select,
    TextField,
    Upload
} from "@vaadin/react-components";
import React, { useState } from "react";
import { useForm } from "@vaadin/hilla-react-form";
import RegisterUserDTO from 'Frontend/generated/hr/tvz/application/dto/RegisterUserDTO';
import RegisterUserDTOModel from "Frontend/generated/hr/tvz/application/dto/RegisterUserDTOModel";
import { useNavigate } from 'react-router-dom';

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
        <div>
            <h1>Pet Care Clinic Registration</h1>
            <div className="registration-form-container">
                <form onSubmit={submit}>
                    <FormLayout>
                        <TextField
                            label="Username"
                            {...field(model.username)}
                        />
                        <PasswordField
                            label="Password"
                            {...field(model.password)}
                        />
                        <TextField
                            label="First Name"
                            {...field(model.firstName)}
                        />
                        <TextField
                            label="Last Name"
                            {...field(model.lastName)}
                        />
                        <TextField
                            label="Street"
                            {...field(model.street)}
                        />
                        <TextField
                            label="ZIP"
                            {...field(model.zip)}
                        />
                        <TextField
                            label="City"
                            {...field(model.city)}
                        />
                        <TextField
                            label="Country"
                            {...field(model.country)}
                        />
                        <Select
                            label="Breed Preferences"
                            {...field(model.breedPreferences)}
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
                            {...field(model.budget)}
                        />
                        <RadioGroup
                            label="Adoption status"
                            {...field(model.openToAdoptions)}
                        >
                            <RadioButton value="true" label="Open for adoptions"></RadioButton>
                            <RadioButton value="false" label="Not open for adoptions"></RadioButton>
                        </RadioGroup>
                        <DatePicker
                            label="From"
                            {...field(model.adoptionFromDate)}
                        />
                        <DatePicker
                            label="To"
                            {...field(model.adoptionToDate)}
                        />
                        <Button theme="primary"  onClick={submit} className="half-width">Register</Button>
                        <Button theme="tertiary" className="half-width" onClick={handleCancel}>Cancel</Button>

                    </FormLayout>
                </form>
            </div>
        </div>
    );
}
