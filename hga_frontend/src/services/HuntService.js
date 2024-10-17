import axios from "axios"

const REST_API_BASE_URL = "http://localhost:8080/hunt"

export const listHunt = () => axios.get(REST_API_BASE_URL)

export const postHunt = (hunt) => axios.post(REST_API_BASE_URL, hunt)

export const putHunt = (hunt) => axios.put(REST_API_BASE_URL, hunt)