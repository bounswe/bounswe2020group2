import './HeaderLabel.less'

export const HeaderLabel = ({ label, children }) => {
    return (
        <div>
            <span className="horizontal-label">{label}:&nbsp;</span>
            <br />
            <span className="horizontal-text">{children}</span>
        </div>
    )
}
