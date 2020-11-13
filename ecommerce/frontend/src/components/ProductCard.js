import React, { useState } from 'react'
import { Button, Rate, Card } from 'antd'
import { PlusCircleOutlined } from '@ant-design/icons'

const { Meta } = Card

export const ProductCard = ({ product }) => {
    const { title, rating, width = 300, price, currency, imageUrl } = product

    return (
        <Card style={{ width: width }} cover={<img alt={title} src={imageUrl} />}>
            <Meta description={price + ' ' + currency} title={title} />
            <Rate allowHalf defaultValue={rating}></Rate>

            <Button type="primary" icon={<PlusCircleOutlined />}>
                Add to Cart
            </Button>
        </Card>
    )
}
