import moment from 'moment'
import { Rate } from 'antd'
import { Link } from 'react-router-dom'
import './VendorReview.less'
export const VendorReview = ({
    review: {
        comment,
        reviewed_by: { firstname, lastname },
        review_date,
        rating,
    },
}) => {
    const dateFmt = moment(review_date).format('LL')

    return (
        <div className="vendor-review-item-content">
            <div className="vendor-review-rate-and-message">
                <div className="vendor-review-rate">
                    <Rate defaultValue={rating} disabled></Rate>
                </div>
                <div className="vendor-review-message">{comment}</div>
            </div>
            <div className="vendor-review-author-and-date">
                <Link to="/">
                    <div className="vendor-review-author">
                        {firstname} {lastname}
                    </div>
                </Link>
                <div className="vendor-review-date">{dateFmt}</div>
            </div>
        </div>
    )
}
