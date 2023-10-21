import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Navbar from './components/navbar/NavigationBar';

import UserForm from './components/navbar/UserForm';
import Home from './components/home/Home';
import AdminPane from './components/admin/AdminPane';
function App() {
	return (
		<BrowserRouter>
			<Navbar />
			<Routes>{/* only one route and component shows at time */}
				<Route path='/' index element={<Home />} />{/* path to the Home component */}
				<Route path='/signup' element={<UserForm />} />
				<Route path='/admin-pane' element={<AdminPane />} />
			</Routes>

		</BrowserRouter>

	);
}

export default App;
