import axios from 'axios'

const BASE_URL = "http://localhost:8080/api/cross"

export const getCrossByJudge = (id) => axios.get(`${BASE_URL}/judge/${id}`)

export const getCrossByDog = (id) => axios.get(`${BASE_URL}/dog/${id}`)

export const postCross = (cross) => axios.post(BASE_URL, cross)

export const deleteCross = (id) => axios.delete(`${BASE_URL}/${id}`)

export const putCross = (id, cross) => axios.put(`${BASE_URL}/${id}`, cross)

export const getAllCrossForDay = (day) => axios.get(`${BASE_URL}/day/${day}`)