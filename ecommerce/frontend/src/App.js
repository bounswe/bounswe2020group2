import logo from './logo.svg'
import './App.less'
import config from './config.js'
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom'
import { Layout, Menu, Row, Col } from 'antd'

const CategoryBar = ({ categories = [] }) => {
    return (
        <div>
            <Menu mode="horizontal" className="category-menu">
                {categories.map(({ key, title }) => {
                    const url = `/categories/${key}`
                    return (
                        <Menu.Item className="category-menu__menu-item" key={key}>
                            <Link to={url}>{title}</Link>
                        </Menu.Item>
                    )
                })}
            </Menu>
        </div>
    )
}

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

const HomePage_Splash = () => {
    return <div style={{ height: 400 }}></div>
}

const HomePage_MainContent = () => {
    return <div style={{ height: 100 }}></div>
}

const Footer = () => {
    const cols = [
        [['About us'], ['My Account'], ['Orders History']],
        [['Contact us'], ['Address'], ['Email'], ['Working Days']],
        [['Follow Us'], ['Facebook'], ['Twitter'], ['Instagram']],
    ]
    return (
        <Layout.Footer className="footer">
            {cols.map((col, ix1) => {
                return (
                    <div key={ix1} className="footer-col">
                        {col.map((entry, ix2) => {
                            return <p key={ix2}>{entry}</p>
                        })}
                    </div>
                )
            })}
        </Layout.Footer>
    )
}

const HomePage = () => {
    const categories = [
        { key: 'electronics', title: 'Electronics' },
        { key: 'home_and_garden', title: 'Home & Garden' },
        { key: 'personal_care', title: 'Personal Care' },
        { key: 'furniture', title: 'Furniture' },
        { key: 'pet_supplies', title: 'Pet Supplies' },
        { key: 'mens_fashion', title: "Men's Fashion" },
        { key: 'womens_fashion', title: "Women's Fashion" },
    ]
    return (
        <div>
            <HomePage_Splash />
            <CategoryBar categories={categories} />
            <HomePage_MainContent />
        </div>
    )
}

const LoginPage = () => {
    return 'login'
}

const SignupPage = () => {
    return 'signup'
}

const Content = () => {
    return (
        <Layout.Content className="content">
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
            <Footer />
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
