import axios from 'axios'

const BASE_URL = 'http://localhost:8080/api/scratch'

export const createScratch = (scratch) => axios.post(BASE_URL, scratch)

export const deleteScratch = (id) => axios.delete(`${BASE_URL}/${id}`)

export const getScratches = () => axios.get(BASE_URL)