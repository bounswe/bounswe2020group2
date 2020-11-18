import React, { useState } from 'react'
import './SearchInput.less'
import { Select, Input } from 'antd'

const { Option } = Select
const { Search } = Input

// TODO - Fetch from a common file or backend
const categories = [
    { key: 'electronics', title: 'Electronics' },
    { key: 'home_and_garden', title: 'Home & Garden' },
    { key: 'personal_care', title: 'Personal Care' },
    { key: 'furniture', title: 'Furniture' },
    { key: 'pet_supplies', title: 'Pet Supplies' },
    { key: 'mens_fashion', title: "Men's Fashion" },
    { key: 'womens_fashion', title: "Women's Fashion" },
]

export const SearchInput = ({ width = 120, initialValue, onSearch = () => {} }) => {
    const [category, setCategory] = useState('Electronics')

    function handleCategoryChange(value) {
        setCategory(value)
    }

    const categoryDropdown = (
        <Select defaultValue={category} style={{ width }} onChange={handleCategoryChange}>
            {categories.map(category => (
                <Option value={category.title} key={category.key}>
                    {category.title}
                </Option>
            ))}
        </Select>
    )

    return (
        <div className="search-input">
            {categoryDropdown}
            <Search
                placeholder="Search for products, categories and brands"
                defaultValue={initialValue}
                onSearch={value => {
                    if (value.trim() === '') {
                        return
                    }
                    onSearch(value.trim())
                }}
                enterButton
            />
        </div>
    )
}
