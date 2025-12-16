import "./ActionModal.css";

export function ActionModal({
    open,
    title,
    description,
    primaryText,
    secondaryText,
    onPrimary,
    onSecondary,
}) {
    if (!open) return null;

    return (
        <div className="modal-backdrop">
            <div className="modal-box">
                <h3>{title}</h3>
                <p>{description}</p>

                <div className="modal-actions">
                    <button
                        className="btn cancel"
                        onClick={onSecondary}
                    >
                        {secondaryText}
                    </button>

                    <button
                        className="btn danger"
                        onClick={onPrimary}
                    >
                        {primaryText}
                    </button>
                </div>
            </div>
        </div>
    );
}
