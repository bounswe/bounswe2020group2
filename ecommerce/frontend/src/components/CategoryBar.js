import { Menu } from 'antd'
import { Link, useHistory } from 'react-router-dom'
import './CategoryBar.less'
import { categories, subcategories } from '../utils.js'
import SubMenu from 'antd/lib/menu/SubMenu'

export const CategoryBar = () => {
    const history = useHistory()

    const onCategoryClick = url => () => {
        history.push({pathname: url})
    }

    return (
        <Menu mode="horizontal" className="category-menu">
            {Object.entries(categories).map(([categoryKey, categoryTitle]) => {
                const categoryURL = `/categories/${categoryKey}`
                return (
                    <SubMenu onTitleClick={onCategoryClick(categoryURL)} title={categoryTitle} className="category-menu__menu-item" key={categoryKey}>
                        {Object.entries(subcategories[categoryKey]).map(([subCategoryKey, subCategoryTitle]) => {
                            const subCategoryURL = categoryURL + `/${subCategoryKey}`
                            return (
                                <Menu.Item key={subCategoryKey} >
                                    <Link to={subCategoryURL}>{subCategoryTitle}</Link>  
                                </Menu.Item>
                            )
                        })}
                    </SubMenu>
                )
            })}
        </Menu>
    )
}
