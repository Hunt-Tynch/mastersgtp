import axios from 'axios';

const BASE_URL = "http://localhost:8080/api/cross";

// Define headers configuration
const config = {
    headers: {
        'Content-Type': 'application/json'
    }
};

export const getCrossByJudge = (id) => axios.get(`${BASE_URL}/judge/${id}`, config);

export const getCrossByDog = (id) => axios.get(`${BASE_URL}/dog/${id}`, config);

export const postCross = (cross) => axios.post(BASE_URL, cross, config);

export const deleteCross = (id) => axios.delete(`${BASE_URL}/${id}`, config);

export const putCross = (id, cross) => axios.put(`${BASE_URL}/${id}`, cross, config);

export const getAllCrossForDay = (day) => axios.get(`${BASE_URL}/day/${day}`, config);

export const getAllCrossForDogAndDay = (day, number) => axios.get(`${BASE_URL}/day/${day}/dog/${number}`, config);
