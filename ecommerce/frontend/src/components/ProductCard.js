import React, { useState } from 'react'
import { BsPlusCircle } from 'react-icons/bs'
import { Button, Rate } from 'antd'
import { Card } from 'antd'

const { Meta } = Card

export const ProductCard = ({ Title, Price, Rating = 0 }) => {
    return (
        <div>
            <Card
                style={{ width: 300 }}
                cover={
                    <img
                        alt="example"
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS9I14rV_Z52uoBWHbruHsl84HI13mv66EW8A&usqp=CAU"
                    />
                }>
                <Meta description={Price} title={Title} />
                <Rate allowHalf defaultValue={Rating}></Rate>
                <span>
                    <Button icon={<BsPlusCircle />}> Add to cart!</Button>
                </span>
            </Card>
        </div>
    )
}
