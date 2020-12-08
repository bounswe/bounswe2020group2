import React, { useEffect, useState } from 'react'
import { Rate, Pagination, Spin } from 'antd'
import { Link } from 'react-router-dom'
import './UserReview.less'
import { sleep } from '../utils'
import { api } from '../api'

export const UserReview = ({ productId = 1, pageSize = 10 }) => {
    const [isLoading, setIsLoading] = useState(true)
    const [currentPage, setCurrentPage] = useState(1)
    const [reviews, setReviews] = useState([])

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                const tmp = [
                    {
                        comment:
                            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Jeff Einstein',
                        date: 'December 23',
                        rating: 4,
                    },
                    {
                        comment: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Hasan Kaya',
                        date: 'December 11',
                        rating: 3,
                    },
                ]
                const urlStr = `/product/${productId}/review?pagesize=${pageSize}&currentpage=${currentPage}`
                console.log(urlStr)
                // const { data: reviews } = await api.get(urlStr)
                setReviews(tmp)
                await sleep(2000)
            } catch (error) {
                console.error('Failed to load user reviews', error)
            } finally {
                setIsLoading(false)
            }
        }
        fetch()
    }, [currentPage])

    const onPaginationChanged = value => {
        console.log('Page changed to ', value)
        setCurrentPage(value)
    }

    const Review = ({ review: { comment, author, date, rating } }) => {
        return (
            <div className="review-item-content">
                <div className="rate-and-author">
                    <div className="rate">
                        <Rate defaultValue={rating} disabled={true}></Rate>
                    </div>
                    <Link to="#">
                        <div className="author">
                            <p>{author}</p>
                        </div>
                    </Link>
                </div>
                <div className="comment-and-date">
                    <p>{comment}</p>
                    <p>{date}</p>
                </div>
            </div>
        )
    }

    return (
        <div>
            <Spin spinning={isLoading}>
                <h1>User Reviews</h1>
                <div className="review-items">
                    {reviews.map((review, index) => {
                        return (
                            <div key={review.id}>
                                <Review review={review} />
                                {index < reviews.length - 1 && <hr></hr>}
                            </div>
                        )
                    })}
                </div>
            </Spin>

            <Pagination
                className="review-results-pagination"
                onChange={onPaginationChanged}
                defaultCurrent={1}
                total={50}
            />
        </div>
    )
}
