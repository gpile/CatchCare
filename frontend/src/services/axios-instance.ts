import toastService from "@/utils/toast";
import { getToken } from "@josempgon/vue-keycloak";
import axios, { AxiosInstance } from "axios";

const axiosInstance: AxiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL, // Ensure this is set in your .env file
});

console.log("Axios instance created with base URL:", axiosInstance.defaults.baseURL);

axiosInstance.defaults.maxRedirects = 0

// Request interceptor for API calls
axiosInstance.interceptors.request.use(
    async config => {
        const token = await getToken()
        config.headers['Authorization'] = `Bearer ${token}`
        return config
    },
    error => {
        Promise.reject(error)
    },
)

axiosInstance.interceptors.response.use(
    function (response) {
        // Any status code that lie within the range of 2xx cause this function to trigger
        // Do something with response data
        return response
    },
    function (error) {
        // Any status codes that falls outside the range of 2xx cause this function to trigger
        // Do something with response error
        toastService.error('Request failed, please try again.')
        return Promise.reject(error)
    },
)

export default axiosInstance
