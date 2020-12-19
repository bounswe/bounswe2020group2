import moment from 'moment'
import { Rate } from 'antd'
import { Link } from 'react-router-dom'
import './UserReview.less'
export const UserReview = ({
    review: {
        comment,
        reviewed_by: { firstname, lastname },
        review_date,
        rating,
    },
}) => {
    const dateFmt = moment(review_date).format('LL')

    return (
        <div className="review-item-content">
            <div className="review-rate-and-message">
                <div className="review-rate">
                    <Rate defaultValue={rating} disabled></Rate>
                </div>
                <div className="review-message">{comment}</div>
            </div>
            <div className="review-author-and-date">
                <Link to="/">
                    <div className="review-author">{firstname.concat(' ', lastname)}</div>
                </Link>
                <div className="review-date">{dateFmt}</div>
            </div>
        </div>
    )
}
