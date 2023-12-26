import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { NextUIProvider } from "@nextui-org/react";
import {ThemeProvider as NextThemesProvider} from "next-themes";
import { AuthProvider } from './context/AuthProvider';


const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <NextUIProvider>
            <NextThemesProvider attribute="class" defaultTheme="dark">
                <AuthProvider>
                    <main className="dark text-foreground bg-content1 h-full">
                        <App />
                    </main>
                </AuthProvider>
            </NextThemesProvider>
        </NextUIProvider>
    </React.StrictMode>
);
