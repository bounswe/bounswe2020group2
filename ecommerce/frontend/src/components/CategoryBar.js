import './CategoryBar.less'

import { Menu } from 'antd'
import qs from 'querystring'
import { useHistory } from 'react-router-dom'

import { useAppContext } from '../context/AppContext'
const { SubMenu } = Menu

export const CategoryBar = () => {
    const history = useHistory()
    const { categories } = useAppContext()

    const onCategoryClick = categoryId => () => {
        history.push({
            pathname: `/search/products`,
            search: qs.stringify({ category: categoryId }),
        })
    }

    const onSubcategoryClick = (category, subcategory) => () => {
        history.push({
            pathname: `/search/products`,
            search: qs.stringify({ category: category.id, subcategory: subcategory.id }),
        })
    }

    return (
        <Menu mode="horizontal" className="category-menu">
            {categories.map(category => {
                return (
                    <SubMenu
                        onTitleClick={onCategoryClick(category.id)}
                        title={category.name}
                        className="category-menu__menu-item"
                        key={category.id}>
                        {category.subcategories.map(subcategory => {
                            return (
                                <Menu.Item key={subcategory.id} onClick={onSubcategoryClick(category, subcategory)}>
                                    {subcategory.name}
                                </Menu.Item>
                            )
                        })}
                    </SubMenu>
                )
            })}
        </Menu>
    )
}
