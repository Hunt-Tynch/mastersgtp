import axios from 'axios'

const REST_API_BASE_URL = "http://localhost:8080/scores"

export const getAllScores = () => axios.get(REST_API_BASE_URL)

export const getAllJudgeScores = (id) => axios.get(REST_API_BASE_URL + '/judge/' + id)

export const getAllDogScores = (number) => axios.get(REST_API_BASE_URL + '/dog/' + number)

export const postScore = (score) => axios.post(REST_API_BASE_URL, score)

export const putScore = (score) => axios.put(REST_API_BASE_URL, score)

export const deleteScore = (id) => axios.delete(REST_API_BASE_URL + '/' + id)
