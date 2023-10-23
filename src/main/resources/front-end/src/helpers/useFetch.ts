import { useState, useEffect } from "react";

export const useFetch = (url: string): any => {
	const [data, setData] = useState<any[]>([]);
	const [isLoading, setIsLoading] = useState<boolean>(true);
	const [error, setError] = useState<DOMException | null>(null);

	useEffect(() => {
		const abortFetch: AbortController = new AbortController();
		const signal: AbortSignal = abortFetch.signal
		fetch(url, { signal })
			.then(response => {
				return response.json();//wait untill this variable it's completely filled to pass in the next then(data =>
			})
			.then(filledData => {//run this method once the first response it's completely filled in the return from above
				setData(filledData);//fill setBlogs() once the return of the first .then is completed
				setIsLoading(false);//once the data is completely filled, set the template loading to false
				setError(null);//if the data is completely filled, set the error template to null
			})
			.catch(e => {
				if (e.name === "AbortError") {
					console.log("fetch aborted, unmount component");
				} else {
					//if the data is not found or there is another kind of error, set the template loading to false and replace it with error
					setIsLoading(false);
					setError(e.message);
				}
			});
		return () => abortFetch.abort();//abort the fetching to unmount the component after switching between components in the page
	}, [url]);

	return { data, isLoading, error };//return from the useFetch
};