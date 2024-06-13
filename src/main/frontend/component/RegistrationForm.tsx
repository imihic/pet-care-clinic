import React, { useState } from 'react';
import {
    TextField,
    PasswordField,
    Button,
    FormLayout,
    Upload,
    RadioButton,
    RadioGroup,
    DatePicker,
    Select,
    NumberField,
    Item,
    TextFieldChangeEvent, PasswordFieldChangeEvent, SelectChangeEvent
} from '@vaadin/react-components';
import { registerUser } from "Frontend/generated/RegistrationEndpoint";
import RegisterUserDTO from "Frontend/generated/hr/tvz/application/dto/RegisterUserDTO";

const RegistrationForm: React.FC = () => {
    const [formData, setFormData] = useState<RegisterUserDTO>({
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        street: '',
        zip: '',
        city: '',
        country: '',
        breedPreferences: '',
        profilePicture: [],
        budget: 0,
        openToAdoptions: true
    });

    const handleChange = (e: TextFieldChangeEvent | PasswordFieldChangeEvent | SelectChangeEvent) => {
        const { name, value } = (e.target as unknown as HTMLInputElement);
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleProfilePictureChange = (event: Event) => {
        const input = event.target as HTMLInputElement;
        const file = input.files ? input.files[0] : null;
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                const arrayBuffer = reader.result as ArrayBuffer;
                const byteArray = new Uint8Array(arrayBuffer);
                setFormData({ ...formData, profilePicture: Array.from(byteArray) });
            };
            reader.readAsArrayBuffer(file);
        } else {
            setFormData({ ...formData, profilePicture: [] });
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await registerUser(formData);
            console.log('User registered successfully:', response);
        } catch (error) {
            console.error('Error registering user:', error);
        }
    };

    return (
        <div className="registration-form-container">
        <form onSubmit={handleSubmit}>
            <FormLayout>
                <TextField
                    label="Username"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                />
                <PasswordField
                    label="Password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                />
                <TextField
                    label="First Name"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                />
                <TextField
                    label="Last Name"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                />
                <TextField
                    label="Street"
                    name="street"
                    value={formData.street}
                    onChange={handleChange}
                />
                <TextField
                    label="ZIP"
                    name="zip"
                    value={formData.zip}
                    onChange={handleChange}
                />
                <TextField
                    label="City"
                    name="city"
                    value={formData.city}
                    onChange={handleChange}
                />
                <TextField
                    label="Country"
                    name="country"
                    value={formData.country}
                    onChange={handleChange}
                />
                <Select
                    label="Breed Preferences"
                    name="breedPreferences"
                    value={formData.breedPreferences}
                    onChange={handleChange}
                >
                    <Item value="Labrador">Labrador</Item>
                    <Item value="Poodle">Poodle</Item>
                </Select>
                <Upload
                    aria-label="Profile picture"
                    accept="image/*"
                    maxFiles={1}
                    onUploadBefore={(e) => {
                        e.preventDefault();
                        handleProfilePictureChange(e as unknown as Event);
                    }}
                />
                <NumberField
                    label="Budget"
                    name="budget"
                    value={formData.budget.toString()}
                    onChange={(e: any) => setFormData({ ...formData, budget: parseFloat(e.target.value) })}
                />
                <RadioGroup
                    label="Adoption status"
                    value={formData.openToAdoptions ? 'true' : 'false'}
                    onChange={(e) => setFormData({ ...formData, openToAdoptions: (e.target as HTMLInputElement).value === 'true' })}
                >
                    <RadioButton value="true" label="Open for adoptions"></RadioButton>
                    <RadioButton value="false" label="Not open for adoptions" ></RadioButton>
                </RadioGroup>
                <DatePicker
                    label="From"
                    name="adoptionFromDate"
                    value={formData.adoptionFromDate}
                    onChange={(e: any) => setFormData({ ...formData, adoptionFromDate: e.target.value })}
                />
                <DatePicker
                    label="To"
                    name="adoptionToDate"
                    value={formData.adoptionToDate}
                    onChange={(e: any) => setFormData({ ...formData, adoptionToDate: e.target.value })}
                />
            </FormLayout>
            <Button theme="primary" className="half-width">Register</Button>
            <Button theme="tertiary" className="half-width">Cancel</Button>
        </form>
        </div>
    );
};

export default RegistrationForm;
