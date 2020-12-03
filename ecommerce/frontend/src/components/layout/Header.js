import './Layout_common.less'
import { Layout, Menu, Avatar, Dropdown, Button } from 'antd'
import { UserOutlined, LogoutOutlined, ProfileOutlined, ShoppingCartOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router-dom'
import { useContext } from 'react'
import { useAppContext } from '../../context/AppContext'
import { SearchInput } from '../SearchInput'

export const GuestHeaderContent = () => {
    return (
        <Menu className="header-menu" mode="horizontal">
            <Menu.Item key="login">
                <Link to="/login">Login</Link>
            </Menu.Item>
            <Menu.Item key="signup">
                <Link to="/signup">Sign up</Link>
            </Menu.Item>
        </Menu>
    )
}

export const CustomerHeaderContent = () => {
    const { user, logout } = useAppContext()
    const history = useHistory()

    const onMenuItemClick = ({ key }) => {
        if (key === 'logout') {
            logout()
            history.push('/')
        } else if (key === 'profile') {
            history.push({
                pathname: '/profile',
            })
        }
    }

    const dropdownMenu = () => {
        return (
            <Menu onClick={onMenuItemClick}>
                <Menu.Item key="profile" icon={<ProfileOutlined />}>
                    Profile
                </Menu.Item>
                <Menu.Item key="logout" icon={<LogoutOutlined />}>
                    Logout
                </Menu.Item>
            </Menu>
        )
    }

    return (
        <div className="header-customer">
            <Link to="/shoppingCart" className="header-customer-cart">
                <Button
                    className="header-customer-cart"
                    icon={<ShoppingCartOutlined className="header-customer-cart-icon" />}
                    ghost>
                    My Cart
                </Button>
            </Link>
            <Dropdown overlay={dropdownMenu()} placement={'bottomRight'} trigger="click">
                <Button className="header-customer-info" ghost>
                    <Avatar shape="square" size="large" icon={<UserOutlined />} />
                    <div className="header-customer-info-details">
                        <div className="header-customer-info-details-name">
                            {user.name} {user.surname}
                        </div>
                        <div className="header-customer-info-details-email">{user.email}</div>
                    </div>
                </Button>
            </Dropdown>
        </div>
    )
}

export const Header = () => {
    const { user } = useAppContext()

    return (
        <Layout.Header className="header">
            <div className="header-logo">
                <Link to="/">getflix</Link>
            </div>
            <div className='header-search-bar'>
                <SearchInput
                    initialValues={''}

                    // initialValues={values.search} onSearch={onSearch}
                />
            </div>
            {user.type === 'guest' ? <GuestHeaderContent /> : <CustomerHeaderContent />}
        </Layout.Header>
    )
}
