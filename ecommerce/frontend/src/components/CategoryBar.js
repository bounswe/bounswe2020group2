import { Menu } from 'antd'
import { Link } from 'react-router-dom'
import './CategoryBar.less'

export const CategoryBar = ({ categories = [] }) => {
    return (
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
    )
}
