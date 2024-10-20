import axios from 'axios'

const REST_API_BASE_URL = 'http://localhost:8080/judges'

export const getJudge = (id) => axios.get(REST_API_BASE_URL + '/' + id)

export const getJudges = () => axios.get(REST_API_BASE_URL)

export const postJudge = (judge) => axios.post(REST_API_BASE_URL, judge)

export const putJudge = (judge) => axios.put(REST_API_BASE_URL, judge)

export const deleteJudge = (id) => axios.delete(REST_API_BASE_URL + '/' + id)