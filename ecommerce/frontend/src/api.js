import axios from 'axios'
import { config } from './config'

export const api = axios.create({
    baseURL: config.apiUrl,
})

const requestInterceptor = request => {
    const token = localStorage.getItem('token')
    if (token) request.headers.authorization = `Bearer ${token}`
    return request
}

console.log('api:attach request interceptor')
api.interceptors.request.use(requestInterceptor)
