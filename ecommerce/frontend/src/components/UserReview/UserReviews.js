import React, { useEffect, useState } from 'react'
import { Pagination, Spin } from 'antd'
import './UserReviews.less'
import { sleep } from '../../utils'
import { api } from '../../api'
import { UserReview } from './UserReview'

export const UserReviews = ({ productId = 1, pageSize = 10, totalPage = 50 }) => {
    const [isLoading, setIsLoading] = useState(true)
    const [currentPage, setCurrentPage] = useState(1)
    const [reviews, setReviews] = useState([])

    useEffect(() => {
        // When current page is changed, makes a call to backend and gets the new reviews.
        async function fetch() {
            try {
                setIsLoading(true)
                const {
                    data: {
                        data: { reviews },
                    },
                } = await api.get(`/product/${productId}/review?page_size=${pageSize}&page=${currentPage}`)
                setReviews(reviews)
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
        // When page count is changed, current page is set
        console.log('Page changed to ', value)
        setCurrentPage(value)
    }

    return (
        <div className="user-review">
            <Spin spinning={isLoading}>
                <h1>User Reviews</h1>
                <div className="user-review-items">
                    {reviews.map((review, index) => {
                        return (
                            <div key={review.id}>
                                <UserReview review={review} />
                                {index < reviews.length - 1 && <hr style={{ margin: 0 }}></hr>}
                            </div>
                        )
                    })}
                </div>
            </Spin>

            <Pagination
                className="review-results-pagination"
                onChange={onPaginationChanged}
                current={currentPage}
                defaultPageSize={pageSize}
                total={totalPage}
            />
        </div>
    )
}
