//Authentication service

import axios from "axios";
import { API_ROOT_PATH } from "./user.service";



const API_URL = `${API_ROOT_PATH}/api/auth/`;

// const register = (username, email, password) => {
//   return axios.post(API_URL + "signup", {
//     username,
//     email,
//     password,
//   });
// };

const login = (username, password) => {
  return axios
    .post(API_URL + "signin", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
//   register,
  login,
  logout,
  getCurrentUser,
};

export default AuthService;