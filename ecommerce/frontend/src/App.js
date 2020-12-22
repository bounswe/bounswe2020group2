import './App.less'

import { BrowserRouter as Router } from 'react-router-dom'

import { MainLayout } from './components/layout/MainLayout'
import { Helmet } from 'react-helmet'
import { useEffect, useState } from 'react'
import { useAppContext } from './context/AppContext'
import { Spin } from 'antd'
import getflixLogo from './assets/logo.png'

import { LoadingOutlined } from '@ant-design/icons'

const loadingSpinner = <LoadingOutlined style={{ fontSize: 42 }} spin />
const LoadingScreen = () => {
    return (
        <div className="app-loading">
            <img src={getflixLogo} className="splash-logo"></img>
        </div>
    )
}

function App() {
    const { init, getCategories } = useAppContext()
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        async function fetch() {
            console.log('App:useEffect:start')
            setLoading(true)
            try {
                await Promise.all([init(), getCategories()])
            } catch (error) {
                console.error('App:useEffect:error', error)
            } finally {
                setLoading(false)
                console.log('App:useEffect:end')
            }
        }
        fetch()
    }, [])

    return (
        <>
            <Helmet>
                <title>Getflix - Get all you want</title>
            </Helmet>
            <Router>
                {loading ? (
                    <Spin className="app-loading-spin" indicator={loadingSpinner} delay={3000} spinning={loading}>
                        <LoadingScreen />
                    </Spin>
                ) : (
                    <MainLayout key={loading} />
                )}
            </Router>
        </>
    )
}

export default App
