import axios from 'axios';

const REST_API_BASE_URL = 'http://localhost:8080/api/hunt';

export const getHunt = () => axios.get(REST_API_BASE_URL);

export const postHunt = (hunt) => axios.post(REST_API_BASE_URL, hunt);

export const putHunt = (hunt) => axios.put(REST_API_BASE_URL, hunt);

export const putStartTime = (day, time) => axios.put(`${REST_API_BASE_URL}/start-time/${day}/${time}`);
