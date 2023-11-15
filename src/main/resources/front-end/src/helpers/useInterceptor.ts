
import axios from "axios";
import { useAuthContext } from "../context/AuthProvider";
import { User } from "../models/user";
import { useEffect } from "react";

const axiosPrivate = axios.create({
	baseURL: 'http://localhost:8080',//spring boot api base url
	headers: { 
		'Content-Type': 'application/json',
		"Access-Control-Allow-Credentials":true
	 },
});
// Add a request interceptor
const useInterceptor = () => {
	const { auth }: ValueContext = useAuthContext();
	const user: User = auth;
	const token: string | undefined = user.token;
	useEffect(() => {
		const requestIntercept = axiosPrivate.interceptors.request.use(
			config => {
				if (token !== undefined) {
					config.headers["Authorization"] = `Bearer ${token}`;
				}
				return config;
			}, (error) => Promise.reject(error)
		);
		return () => {
			axiosPrivate.interceptors.request.eject(requestIntercept);
		};
	}, [token]);

	return axiosPrivate;
}

export default useInterceptor;