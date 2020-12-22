import './App.less'

import { BrowserRouter as Router } from 'react-router-dom'

import { MainLayout } from './components/layout/MainLayout'
import { Helmet } from 'react-helmet'
import { useEffect } from 'react'
import { useAppContext } from './context/AppContext'

function App() {
    const { init } = useAppContext()

    useEffect(() => {
        init()
    }, [])

    return (
        <>
            <Helmet>
                <title>Getflix - Get all you want</title>
            </Helmet>
            <Router>
                <MainLayout />
            </Router>
        </>
    )
}

export default App
