    //Data service
import axios from "axios";
import authHeader from "./auth-header";

// export const API_ROOT_PATH = "https://anybus.online"
// export const API_ROOT_PATH = "http://localhost:8080"
export const API_ROOT_PATH = process.env.REACT_APP_API_ROOT_PATH  || "http://localhost:8080";


const API_URL = "";


//Public APIs
export const apiGetHomeScheduleItems = (queryDate, queryDir, queryStop, bstopTo) => {
  // if (bstopTo)
    return axios.get(API_ROOT_PATH + `/api/scheduleItem/home-from?qdate=${queryDate}&qdir=${queryDir}&qbstopfrom=${queryStop}&qbstopto=${bstopTo}`)
  // else
    // return axios.get(API_ROOT_PATH + `/api/scheduleItem/home-fromto?qdate=${queryDate}&qdir=${queryDir}&qbstopfrom=${queryStop}`)  
}

export const getTripInfo = (tripId) => {
    return axios.get(API_ROOT_PATH + `/api/scheduleItem/singleTrip/${tripId}` )
}

export const apiGetSelectBusStopSearchOptions = (query) => {
  return axios.get(`${API_ROOT_PATH}/api/busstop/search?str=${query}`)
}

export const apiGetBusStopDtoFromAndTo = (from, to) => {
  console.log("calling get2 API")
  return axios.get(`${API_ROOT_PATH}/api/busstop/get2?fromId=${from}&toId=${to}`)
}

export const apiGetScheduleAndSingleTrip = (tripId) => {
  return axios.get(`${API_ROOT_PATH}/api/scheduleItem/schedule-by-tripid?tripID=${tripId}`)
}

export const apiGetLinesPreviewPublic = (lineId) => {
  return axios.get(`${API_ROOT_PATH}/api/line/line-preview-withid-public`)
}

export const apiGetSchedulesByLineBrowse = (lineId) => {
  return axios.get(`${API_ROOT_PATH}/api/scheduleItem/schedule-by-lineid-browse?lineId=${lineId}`)
}

//Authorized APIs

//Holidays
export const apiGetAllHolidays = () => {
  return axios.get(API_ROOT_PATH + "/api/holidays/all", {headers: authHeader()})
}

export const apiPostHolidaysSave = (newHoliday) => {
  return axios.post(`${API_ROOT_PATH}/api/holidays/save1`, newHoliday, {headers: authHeader()} )
}

export const apiHolidayDel = (id) => {
  axios.delete(`${API_ROOT_PATH}/api/holidays/del1?id=${id}`, {headers: authHeader()})
}

//Bus stops

export const apiGetBusStopsAll = () => {
  return axios.get(`${API_ROOT_PATH}/api/busstop/get/all`, {headers: authHeader()})
}

export const apiGetAllStopsAndUsage = () => {
  return axios.get(`${API_ROOT_PATH}/api/busstop/get-all-with-usage`, {headers: authHeader()})
}

export const apiPostBusStopSave = (formData) => {
  return axios.post(`${API_ROOT_PATH}/api/busstop/save/one`, formData, {headers: authHeader()})
}

export const apiGetBusStopSearch = (query) => {
  return axios.get(`${API_ROOT_PATH}/api/busstop/searchresults?query=${query}`, {headers: authHeader()})
}

export const apiDelBusStop = (id) => {
  axios.delete(`${API_ROOT_PATH}/api/busstop/delete/${id}`, {headers: authHeader()})
}

//Bus lines

export const apiGetLinesPreview = () => {
  return axios.get(`${API_ROOT_PATH}/api/line/preview`, {headers: authHeader()})
}

// export const apiGetLineEager = (id) => {
//   return axios.get(`${API_ROOT_PATH}/api/line/line-eager?id=${id}`, {headers: authHeader()})
// }

export const apiPostLineEager = (data) => {
  return axios.post(`${API_ROOT_PATH}/api/line/post-line-full-dto`, data, {headers: authHeader()})
}

export const apiGetLineFull = (id) => {
  return axios.get(`${API_ROOT_PATH}/api/line/get-line-full?id=${id}`, {headers: authHeader()})
}

export const apiDelLine = (id) => {
  return axios.delete(`${API_ROOT_PATH}/api/line/delete/${id}`, {headers: authHeader()})
}

export const apiGetEmptyLine = () => {
  return axios.get(`${API_ROOT_PATH}/api/line/get-empty`, {headers: authHeader()})
}

//Schedules

export const apiGetSchedules = (lineId) => {
  return axios.get(`${API_ROOT_PATH}/api/scheduleItem/schedule-by-line?lineId=${lineId}`, {headers: authHeader()})
}

export const apiPostSchedules = (data) => {
  return axios.post(`${API_ROOT_PATH}/api/scheduleItem/post-schedule-dto`, data, {headers: authHeader()})
}

export const apiDelScheduleById = (schedId) => {
  return axios.delete(`${API_ROOT_PATH}/api/scheduleItem/delete/${schedId}`, {headers: authHeader()})
}

//Routes

export const apiGetRoutesByLine = (lineId) => {
  return axios.get(`${API_ROOT_PATH}/api/route/byline/${lineId}`, {headers: authHeader()})
}

//Yearly Rules

export const apiGetAllYearlyRules = () => {
  return axios.get(`${API_ROOT_PATH}/api/yearly-rules/get-all`, {headers: authHeader()})
}

export const apiPostYearlyRulesCombo = (data) => {
 return axios.post(`${API_ROOT_PATH}/api/yearly-rules/post-combo-list`, data, {headers: authHeader()})
}

export const apieGetSchedulesByRule = (ruleId) => {
  return axios.get(`${API_ROOT_PATH}/api/scheduleItem/get-schedules-by-rule?ruleId=${ruleId}`, {headers: authHeader()})
}