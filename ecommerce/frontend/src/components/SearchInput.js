import React from 'react'
import './SearchInput.less'
import { Select } from 'antd'
import Search from 'antd/lib/input/Search'

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

const { Option } = Select

function handleCategoryChange(value) {
    console.log(`selected ${value}`)
}

const categoryDropdown = (
    <Select defaultValue="Electronics" style={{ width: 120 }} onChange={handleCategoryChange}>
        {categories.map(category => (
            <Option value={category.title}>
                {category.title} key = {category.key}
            </Option>
        ))}
    </Select>
)

const onSearchInput = value => {
    if (value === '') {
        return
    }
    // Trigger products page
    console.log(value)
}

export const SearchInput = () => {
    return (
        <div className="search-input">
            {categoryDropdown}
            <Search placeholder="Ürün, kategori veya marka ara" onSearch={onSearchInput} enterButton />
        </div>
    )
}
