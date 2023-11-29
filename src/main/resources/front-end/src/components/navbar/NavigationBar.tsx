
import {Navbar, NavbarBrand, NavbarContent, NavbarItem, Button} from "@nextui-org/react";
import { Link } from "react-router-dom";
import { useAuthContext } from "../../context/AuthProvider";
import { UserAuth } from "../../models/types";


const NavigationBar = () => {
	const { auth }: UserAuth = useAuthContext();
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
				{auth.isLoggedIn === undefined && <NavbarItem>
														<Button as={Link} color="secondary" to="/signup" variant="flat">
															Sign Up
														</Button>
													</NavbarItem>
				}
				<NavbarItem>
					{auth.isLoggedIn === true ?
						<Button as={Link} color="success" to="/login" variant="flat">
							Logged in!
						</Button>
						:
						<Button as={Link} color="primary" to="/login" variant="flat">
							Log in
						</Button>
					}
				</NavbarItem>
			</NavbarContent>
		</Navbar>
	);
};

export default NavigationBar;