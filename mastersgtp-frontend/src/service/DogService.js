import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/dog';

// Create a new dog
export const createDog = (dog) => {
    return axios.post(BASE_URL, dog);
};

// Add a batch of dogs
export const addDogsBatch = (dogs) => {
    return axios.post(`${BASE_URL}/batch`, dogs);
};

// Edit an existing dog
export const editDog = (dog) => {
    return axios.put(BASE_URL, dog);
};

// Get a dog by its number
export const getDogByNumber = (number) => {
    return axios.get(`${BASE_URL}/${number}`);
};

// Get all dogs
export const getAllDogs = () => {
    return axios.get(BASE_URL);
};

// Get dogs sorted by total
export const getDogsByTotal = () => {
    return axios.get(`${BASE_URL}/by-total`);
};

// Get dogs by total and stake type
export const getDogsByTotalAndStake = (stake) => {
    return axios.get(`${BASE_URL}/by-total-and-stake/${stake}`);
};

// Get dogs by owner
export const getDogsByOwner = (owner) => {
    return axios.get(`${BASE_URL}/by-owner/${owner}`);
};

// Get dogs by owner and total
export const getDogsByOwnerAndTotal = (owner) => {
    return axios.get(`${BASE_URL}/by-owner-and-total/${owner}`);
};

export const deleteDog = (number) => axios.delete(BASE_URL + '/' + number)
