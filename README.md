# Gridnine Test Task

Решение данной задачи сводится к обычной фильтрации коллекции.
В Java уже есть замечательный метод filter() в Stream api, который позволяет отфильтровать элементы коллекции.
Поэтому я добавил метод, который принимает List и необходимый набор условий. Метод возвращает List, содержащий только элементы, удовлетворяющие ВСЕМ условиям.


В классы Flight и Segment я добавил методы equals() и hashcode(), для того, чтобы можно было сравнивать объекты этих классов в юнит тестах.
