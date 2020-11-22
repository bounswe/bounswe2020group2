import React, { useState } from 'react'
import './SearchInput.less'
import { Select, Input } from 'antd'

const { Option } = Select
const { Search } = Input

export const SearchInput = ({ width = 120, initialValues, onSearch = () => {} }) => {
    const [type, setType] = useState(initialValues.type ?? 'products')
    const [query, setQuery] = useState(initialValues.query)

    const onTypeChange = value => setType(value)
    const onQueryChange = value => setQuery(value.target.value)

    const _onSearch = () => {
        if (!query || !query.trim()) return
        onSearch({ type, query })
    }

    return (
        <div className="search-input">
            <Select style={{ width }} value={type} onChange={onTypeChange} bordered={false}>
                <Option value={'products'}>Products</Option>
                <Option value={'vendors'}>Vendors</Option>
            </Select>
            <Search
                placeholder={`Search for ${type}`}
                value={query}
                onChange={onQueryChange}
                onSearch={_onSearch}
                enterButton
            />
        </div>
    )
}
