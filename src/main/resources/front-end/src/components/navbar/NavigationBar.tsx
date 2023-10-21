
import {Navbar, NavbarBrand, NavbarContent, NavbarItem, Button} from "@nextui-org/react";
import { Link } from "react-router-dom";


const NavigationBar = () => {
	return (
		<Navbar position="sticky" isBlurred={true} className="bg-secondary-50">
			<NavbarBrand >
				<Link to="/" >
					<p className="font-bold text-inherit text-gray-300 text-3xl">Blog Sample</p>
				</Link>
			</NavbarBrand>

			<NavbarContent className="hidden sm:flex gap-4" justify="start">
				<NavbarItem>
					<Link color="foreground" to="/admin-pane">
						Admin Features
					</Link>
				</NavbarItem>
			</NavbarContent>

			<NavbarContent justify="end">
				<NavbarItem>
					<Button as={Link} color="secondary" to="/signup" variant="flat">
						Sign Up
					</Button>
				</NavbarItem>
			</NavbarContent>
		</Navbar>
	);
};

export default NavigationBar;