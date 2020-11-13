import './App.less'

import { BrowserRouter as Router } from 'react-router-dom'

import { AppContextProvider } from './context/AppContext'
import { MainLayout } from './components/layout/MainLayout'
import { Helmet } from 'react-helmet'

function App() {
    return (
        <AppContextProvider>
            <Helmet>
                <title>Getflix - Get all you want</title>
            </Helmet>
            <Router>
                <MainLayout />
            </Router>
        </AppContextProvider>
    )
}

export default App
