import './App.less'

import { BrowserRouter as Router } from 'react-router-dom'

import { AppContextProvider } from './context/AppContext'
import { MainLayout } from './components/layout/MainLayout'

function App() {
    return (
        <AppContextProvider>
            <Router>
                <MainLayout />
            </Router>
        </AppContextProvider>
    )
}

export default App
