import useMousePosition from './hooks/useMousePosition';

export default function CustomCursor() {
	const mousePosition = useMousePosition();

	return (
		<div
			style={{
				position: 'absolute',
				top: mousePosition.y,
				left: mousePosition.x,
				fontSize: '2rem',
			}}>
			<span role='img' aria-label='atomic symbol'>
				⚛️
			</span>
		</div>
	);
}
