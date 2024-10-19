import axios from 'axios'
const REST_API_BASE_URL = "http://localhost:8080/kennels"

export const listKennel = () => axios.get(REST_API_BASE_URL)

export const makeKennel = (dto) => axios.post(REST_API_BASE_URL, dto)

export const deleteKennel = (id) => axios.delete(REST_API_BASE_URL + '/' + id)