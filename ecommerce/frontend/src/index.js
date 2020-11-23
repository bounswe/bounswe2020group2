import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'

import { AppContextProvider } from './context/AppContext'
import { GoogleLogin } from 'react-google-login'

const onSuccess = (...x) => console.log('success', ...x)
const onFailure = (...x) => console.log('failure', ...x)

ReactDOM.render(
    // <React.StrictMode>
    <AppContextProvider>
        {/* <App /> */}
        <GoogleLogin
            clientId="780650655620-8qi5er6094ouirlb66b2c0hm6hlfo9s8.apps.googleusercontent.com"
            buttonText="CLICK ME!!! GOOGLE!!!"
            onSuccess={onSuccess}
            onFailure={onFailure}
            cookiePolicy={'single_host_origin'}
        />
        ,
    </AppContextProvider>,
    // </React.StrictMode>,
    document.getElementById('root'),
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: rep ortWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
