import axios from 'axios'

const REST_API_BASE_URL = "http://localhost:8080/dogs"

export const getAllDogs = () => axios.get(REST_API_BASE_URL + '/stake')

export const getDogsByStake = (stake) => axios.get(REST_API_BASE_URL + '/stake/' + stake)

export const getDogsByKennel = (id) => axios.get(REST_API_BASE_URL + '/kennel/' + id)

export const getDog = (number) => axios.get(REST_API_BASE_URL + '/' + number)

export const postDogs = (dogs) => axios.post(REST_API_BASE_URL, dogs)

export const deleteDog = (number) => axios.delete(REST_API_BASE_URL + '/' + number)