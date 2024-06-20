import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import {
    VerticalLayout,
    HorizontalLayout,
    Button,
    Icon,
} from '@vaadin/react-components';
import { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import { Icon as LeafletIcon } from 'leaflet';
import { NewsEndpoint } from "Frontend/generated/endpoints"; // Import the news client
import { getAuthenticatedUser } from "Frontend/generated/UserEndpoint";
import User from "Frontend/generated/hr/tvz/application/data/User";
import Role from "Frontend/generated/hr/tvz/application/util/Role";

export const config: ViewConfig = {
    menu: { order: 0, icon: 'line-awesome/svg/home-solid.svg' },
    title: 'Home',
    loginRequired: true,
};

interface Pet {
    headline: string;
    text: string;
    image: string; // assuming the image is now in Base64 format from the backend
}

interface NewsItem {
    id: number;
    title: string;
    description: string;
}

const fakePetsData: Pet[] = [
    {
        headline: 'King',
        text: 'King is a charming and affectionate Bulldog with a playful attitude...',
        image: 'data:image/jpeg;base64,', // replace with actual Base64 data
    },
    {
        headline: 'Jackie',
        text: 'Jackie is a lively and intelligent Siberian Husky, always on the move...',
        image: 'data:image/jpeg;base64,', // replace with actual Base64 data
    },
];

const userAddress = {
    lat: 51.505,
    lng: -0.09,
};

export default function HomeView() {
    const [pets, setPets] = useState<Pet[]>([]);
    const [news, setNews] = useState<NewsItem[]>([]);
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        setPets(fakePetsData);

        // Fetch active news from the API
        NewsEndpoint.getActiveNews().then(activeNews => {
            // Ensure the data is in the correct format
            const formattedNews = activeNews.map(news => ({
                id: news?.id || 0,
                title: news?.title || '',
                description: news?.content || '',
            }));
            setNews(formattedNews);
        });

        // Fetch user details to check if the user is an admin
        getAuthenticatedUser().then((user: User | undefined) => {
            if (user) {
                setIsAdmin(user.roles.some(role => role === Role.ADMIN)); // Assuming role has a 'name' property
            }
        });
    }, []);

    const removeNewsItem = (id: number) => {
        setNews(news.filter(item => item.id !== id));
    };

    const customIcon = new LeafletIcon({
        iconUrl: 'path/to/marker-icon.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
    });

    return (
        <VerticalLayout className="h-full w-full p-l">
            <VerticalLayout className="w-full p-l" style={{ margin: 'auto' }}>
                <h2>News {isAdmin && <Button>Edit</Button>}</h2>
                {news.map(item => (
                    <HorizontalLayout key={item.id} style={{ position: 'relative', border: '1px solid #ccc', borderRadius: '8px', padding: '16px', marginBottom: '16px', width: '100%', alignItems: 'center' }}>
                        <Icon icon="vaadin:exclamation-circle" style={{ marginRight: '16px' }}></Icon>
                        <div style={{ flex: 1 }}>
                            <b>{item.title}</b>
                            <p>{item.description}</p>
                        </div>
                        <Button style={{ marginRight: '16px' }}>See details</Button>
                        <Button theme="icon">
                            <Icon icon="lumo:cross" theme="tertiary" onClick={() => removeNewsItem(item.id)}></Icon>
                        </Button>
                    </HorizontalLayout>
                ))}
            </VerticalLayout>

            <HorizontalLayout className="w-full p-l" style={{ margin: 'auto' }}>
                <VerticalLayout style={{ width: '70%' }}>
                    <h2>Featured pets {isAdmin && <Button>Edit</Button>}</h2>
                    {pets.map((pet, index) => (
                        <HorizontalLayout key={index} style={{ border: '1px solid #ccc', borderRadius: '8px', padding: '16px', marginBottom: '16px', width: '100%', alignItems: 'center' }}>
                            <div style={{ flex: 1 }}>
                                <h3>{pet.headline}</h3>
                                <p>{pet.text}</p>
                            </div>
                            <img src={pet.image} alt={pet.headline} style={{ width: '150px', height: '150px', objectFit: 'cover', borderRadius: '8px', marginLeft: '16px' }} />
                        </HorizontalLayout>
                    ))}
                </VerticalLayout>

                <VerticalLayout style={{ margin: 'auto' }}>
                    <h2>Shelters near you</h2>
                    <MapContainer center={[userAddress.lat, userAddress.lng]} zoom={13} style={{ height: '400px', width: '100%' }}>
                        <TileLayer
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        />
                        <Marker position={[userAddress.lat, userAddress.lng]} icon={customIcon}>
                            <Popup>
                                You are here
                            </Popup>
                        </Marker>
                    </MapContainer>
                </VerticalLayout>
            </HorizontalLayout>
        </VerticalLayout>
    );
}
