import logo from './logo.svg'
import './App.less'
import config from './config.js'

function App() {
    return (
        <div className="App">
            <header className="App-header">
                Backend API URL: {config.apiUrl ?? 'null'} <br />
                Environment: {process.env.NODE_ENV ?? 'null'} <br />
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                    Edit <code>src/App.js</code> and save to reload.
                </p>
                <a className="App-link" href="https://reactjs.org" target="_blank" rel="noopener noreferrer"></a>
            </header>
        </div>
    )
}

export default App
