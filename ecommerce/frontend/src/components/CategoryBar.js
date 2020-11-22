import { Menu } from 'antd'
import { Link, useHistory } from 'react-router-dom'
import './CategoryBar.less'
import { categories, subcategories } from '../utils.js'
import SubMenu from 'antd/lib/menu/SubMenu'
import qs from 'querystring'

export const CategoryBar = () => {
    const history = useHistory()

    const onCategoryClick = category => () => {
        history.push({
            pathname: `/search/products`,
            search: qs.stringify({
                category,
            }),
        })
    }

    const onSubcategoryClick = (category, subcategory) => () => {
        history.push({
            pathname: `/search/products`,
            search: qs.stringify({
                category,
                subcategory,
            }),
        })
    }

    return (
        <Menu mode="horizontal" className="category-menu">
            {Object.entries(categories).map(([categoryKey, categoryTitle]) => {
                return (
                    <SubMenu
                        onTitleClick={onCategoryClick(categoryKey)}
                        title={categoryTitle}
                        className="category-menu__menu-item"
                        key={categoryKey}>
                        {Object.entries(subcategories[categoryKey]).map(([subCategoryKey, subCategoryTitle]) => {
                            return (
                                <Menu.Item
                                    key={subCategoryKey}
                                    onClick={onSubcategoryClick(categoryKey, subCategoryKey)}>
                                    {subCategoryTitle}
                                </Menu.Item>
                            )
                        })}
                    </SubMenu>
                )
            })}
        </Menu>
    )
}
