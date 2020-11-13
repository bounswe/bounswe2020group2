import logo from './logo.svg'
import './App.less'
import config from '../config.js'
import { Layout, Menu, Row, Col } from 'antd'
import getflixLogo from './assets/logo.png'
import { MainLayout } from './layout/MainLayout'
import { BrowserRouter as Router } from 'react-router-dom'

function App() {
    return (
        <Router>
            <MainLayout />
        </Router>
    )
}

export default App
