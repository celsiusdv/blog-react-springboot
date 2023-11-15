import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { NextUIProvider } from "@nextui-org/react";
import { AuthProvider } from './context/AuthProvider';


const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <NextUIProvider>
            <AuthProvider>
                <main className="dark text-foreground bg-content1">
                    <App />
                </main>
            </AuthProvider>
        </NextUIProvider>
    </React.StrictMode>
);
