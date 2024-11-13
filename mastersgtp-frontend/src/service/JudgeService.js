import axios from 'axios'

const REST_API_BASE_URL = "http://localhost:8080/api/judge"

export const getJudges = () => axios.get(REST_API_BASE_URL)

export const postJudge = (judge) => axios.post(REST_API_BASE_URL, judge)

export const deleteJudge = (id) => axios.delete(REST_API_BASE_URL + '/' + id)

export const putJudge = (id, judge) => axios.put(REST_API_BASE_URL + '/' + id, judge)
