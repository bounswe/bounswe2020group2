import logo from './logo.svg'
import './App.less'
import config from './config.js'
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom'
import { Layout, Menu } from 'antd'

const Header = () => {
    return (
        <Layout.Header className="header">
            <div className="header-logo">
                <Link to="/">Getflix</Link>
            </div>
            <Menu className="header-menu" mode="horizontal">
                <Menu.Item key="login">
                    <Link to="/login">Login</Link>
                </Menu.Item>
                <Menu.Item key="signup">
                    <Link to="/signup">Sign up</Link>
                </Menu.Item>
            </Menu>
        </Layout.Header>
    )
}

const HomePage = () => {
    return 'home'
}

const LoginPage = () => {
    return 'login'
}

const SignupPage = () => {
    return 'signup'
}

const Content = () => {
    return (
        <Layout.Content>
            <Switch>
                <Route path="/login">
                    <LoginPage />
                </Route>
                <Route path="/signup">
                    <SignupPage />
                </Route>
                <Route path="/">
                    <HomePage />
                </Route>
            </Switch>
        </Layout.Content>
    )
}

const MainLayout = () => {
    return (
        <Layout>
            <Header />
            <Content />
        </Layout>
    )
}

function App() {
    return (
        <Router>
            <MainLayout />
        </Router>
    )
}

export default App
