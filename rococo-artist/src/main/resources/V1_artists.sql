create extension if not exists "uuid-ossp";

create table if not exists "artist"
(
    id        UUID unique        not null default uuid_generate_v1(),
    name      varchar(50) unique not null,
    biography varchar(1000),
    photo     bytea,
    primary key (id)
);

alter table "artist"
    owner to postgres;

insert into artist (name, biography)
values ('Василий Суриков',
        '(1848-1916) За мастерское овладение приемами композиции друзья-художники в Петербурге называли потомственного казака Василия Сурикова «композитором», однако в академических кругах Сурикова долгое время критиковали как раз за скученность композиций, за «кашу» из лиц персонажей, презрительно называли его полотна «парчовыми коврами». История расставила все по местам - Суриков и сегодня считается непревзойденным мастером живописи, а его исторические полотна - одними из самых реалистичных.'),
       ('Иван Шишкин',
        '(1832-1898) Самого знаменитого русского пейзажиста Ивана Шишкина нызывали «лесным богатырем-художником», «царем леса», «стариком-лесовиком».  Одной из культовых картин картин Шишкина стало полотно «Утро в сосновом бору». Справедливости ради стоит сказать, что медведей на картине писал художник Савицкий, но Павел Третьяков стёр его подпись,поэтому автором картины часто указывается один Шишкин. В советское время эту картину стали называть «Три медведя» (хотя на картине их четыре), из-за одноименной марки шоколада фабрики «Красный Октябрь».'),
       ('Иван Айвазовский',
        '(1817-1900) непрезойденный маринист и один из самых дорогих художников. В 2012 году на британском аукционе Sotheby''s его картина «Вид Константинополя и Босфора» была продана за 3 миллиона 230 тысяч фунтов стерлингов, что в переводе на рубли составляет более 153 миллионов.'),
       ('Виктор Васнецов',
        '(1842-1926) Виктор Васнецов - великий русский художник-живописец и архитектор, мастер исторической и фольклорной живописи. Его называли «истинным богатырем русской живописи». Для большинства Васнецов является создателем мира русских сказок и былин, но он также серьезно занимался архитектурой (фасад Третьяковской галереи) и создавал почтовые марки.'),
       ('Исаак Левитан',
        '(1860-1900) мастер «пейзажа настроения» и самых медитативных полотен в русском искусстве.  Шедевр Левитана «Над вечным покоем» называют «самой русской картиной». Художник писал её под звуки траурного марша из «Героической симфонии» Бетховена.  Один из друзей Левитана  назвал эту картину «реквиемом самому себе».'),
       ('Карл Брюллов',
        '(1799-1852) Восхищенные современники называли Брюллова «великим, божественныйм Карлом» и «вторым Рафаэлем». Белинский окрестил Брюллова «первым художником Европы». Картина «Последний день Помпеи» была признана совершенным шедевром XIX века. Вальтер Скотт просидел целый час за её просмотром, после чего признал: «Это не картина, это целая поэма». На полотне Брюллов изобразил и самого себя - слева от центра, с ящиком с красками и кистями'),
       ('Алексей Саврасов',
        '(1830-1897) великий русский пейзажист, художник-передвижник и учитель Левитана, Коровина и Нестерова, но его нередко называют «художником одной картины». Речь, конечно, о полотне «Грачи прилетели». Исаак Левитан писал про своего учителя: «С Саврасова появилась лирика в живописи пейзажа и безграничная любовь к своей родной земле <…> и эта его несомненная заслуга никогда не будет забыта в области русского художества».'),
       ('Иван Крамской',
        '(1837-1887) Художник-передвижник Иван Крамской, автор картины «Христос в пустыне», создал одну из самых загадочных картин - «Неизвестную», которую также часто называют «Незнакомкой». С кем только её ни соотносили. И с Анной Карениной, и с Настальей Филлиповной, и с дочерью художника Софьей, и с крестьянкой Матреной Саввишной, которая стала женой дворянина Бестужева, и с княжной Варварой Туркестанишвили – фрейлиной императрицы Марии Федоровны, фавориткой Александра І, которому она родила дочь, а после покончила с собой. Версий масса, но «Незнакомка» по-прежнему незнакомка.'),
       ('Илья Репин',
        '(1844-1930) Илья Репин – гениальный портретист, мастер бытовых зарисовок и создатель скандальных исторических полотен. О работе ещё молодого Репина «Бурлаки на Волге» упоенно писали газетчики. Одни зрители ругали ее, другие – восторгались. Картина вызвала живейший интерес у Достоевского и Перова, а между тем некоторые называли ее «величайшей профанацией искусства».'),
       ('Константин Маковский',
        '(1839-1915) Одни называли Константина Маковского предвестником русского импрессионизма, другие считали, что он предает идеалы передвижничества, но, несмотря на критические оценки, Маковский был одним из самых востребованных и высокооплачиваемых художников своего времени. На Всемирной выставке 1889 года в Париже он получил Большую золотую медаль за картины «Смерть Ивана Грозного», «Суд Париса» и «Демон и Тамара».'),
       ('Андрей Рублев',
        '(1360-1430) Несмотря на то, что Андрей Рублев - самый известный русский иконописец, мы знаем о нем не так много. Имя Андрей он получил в монашестве, мирское его имя неизвестно. Непревзойденным шедевром Рублева традиционно считается икона Святой Троицы, написанная в первой четверти XV века. Рублев также был одним из мастеров, кто расписывал Успенский собор во Владимире, Успенский собор в Звенигороде и Благовещенский собор московского Кремля.'),
       ('Валентин Серов',
        '(1865-1911) Друзья называли Валентина Серова «Антошей» - таково было его домашнее прозвище. Знаменитым художник стал после выхода в свет своих шедевров - «Девочки с персиками» и «Девушки, освещенной солнцем». На первой картине была изображена дочь Саввы Мамонтова Вера, на второй - двоюродная сестра самого Серова Машенька Симонович.'),
       ('Архип Куинджи',
        '(1842-1910) Одни называли Куинджи «русским Моне» за виртуозное раскрытие возможности краски. Другие обвиняли художника в стремлении к дешевым эффектам, использовании тайных приемов, вроде скрытой подсветки полотен. В конце концов, на пике шума вокруг своего имени Архип Иванович просто ушел в добровольное изгнание на 30 лет.'),
       ('Михаил Врубель',
        '(1856-1910) Одного из самых трагических русских художников, Врубеля называли творцом искусства, близкого по природе ночным сновидениям. Про увлеченность хужожника образом Демона Александр Бенуа рассказывал: «Верится, что Князь Мира позировал ему.... Его безумие явилось логическим финалом его демонизма». Врубель обладал удивительным психологическим свойством - эйдетизмом. Это особый вид зрительной образной памяти, когда человек не вспоминает, не представляет себе в уме предмет или образ, а видит его, как на фотографии или на экране.'),
       ('Александр Иванов',
        '(1806-1858) Самая известная картина Александр Иванова - «Явление Христа народу». Над ней художник работал больше 20 лет. Это одна из самых загадочных картин в истории русской живописи. Интересно, что в зеркальной копии картины Мессия идет не навстречу к людям, а уходит (удаляется) или проходит мимо. Иванов также делал акварельные эскизы к росписям «Храма человечества». Эти рисунки стали известны только после смерти художника. В историю искусства этот цикл вошел под называнием «библейские эскизы». Они были изданы более 100 лет назад в Берлине и с тех пор больше не переиздавались.'),
       ('Михаил Нестеров',
        '(1862-1942) Михаил Нестеров - выдающийся русский живописец. Он «избегал изображать сильные страсти», отдавая предпочтение тихому пейзажу и человеку, «живущему внутренней жизнью». Его первая картина из цикла о жизни Сергия Радонежского «Видение отроку Варфоломею», в которой станковая живопись включает в себя элементы иконописи, вызвала у критиков массу вопросов. Золотое сияние вокруг головы схимника породило споры не только среди зрителей, увидевших картину на очередной выставке передвижников, но и среди коллег-художников, некоторые из которых назвали картину «вредной».'),
       ('Феофан Грек',
        '(около 1340 — около 1410) В Новгород Феофан Грек приехал уже состоявшимся иконописцем. Он родился в Византии и расписывал храмы Константинополя, Халкидона, генуэзской Галаты и Кафы. Феофан расписывал церковь Преображения в Новгороде, где до сих пор сохранились его фрески, храмы московского Кремля. В Благовещенском соборе Феофан Грек создал первый в России иконостас, где святые были изображены в полный рост. Кроме написания икон и росписи храмов, Феофан Грек также создавал миниатюры для книг и оформлял Евангелия.'),
       ('Константин Коровин',
        '(1861-1939) Главный представитель русского импрессионизма, Константин Коровин во время Первой мировой войны работал консультантом по маскировке в штабе русской армии. В это же время, несмотря на суровую реальность, Коровину удается живописать картины о «прекрасной эпохе». Недаром Коровина называют живописцем радости и счастья.'),
       ('Борис Кустодиев',
        '(1878-1927) Александр Бенуа был убежден, что «настоящий Кустодиев – это русская ярмарка, «глазастые» ситцы, варварская «драка красок», русский посад и русское село, с их гармониками, пряниками, расфуфыренными девками и лихими парнями». Нельзя не вспомнить о «кустодиевских женщинах» - типе русских красавиц, созданном Борисом Михайловичем. В 1912 году Кустодиев начинает работу над галереей непревзойденных женских образов. В 1915 году свет увидел «Купчиху» и «Красавицу» - неповторимые образы русской красоты.'),
       ('Дионисий',
        '(ок. 1440—1502) Имя Дионисия олицетворяет, пожалуй, лучшие и крупнейшие достижения московской иконописи XV-XVI веков. Продожатель традиции Андрея Рублева, Дионисий расписывал много храмов, но истинное понимание манеры письма Дионисия можно получить по великолепно сохранившимся фрескам Ферапонтова монастыря на Белоозере. Они никогда не переписывались и не подвергались серьезной реставрации.'),
       ('Александр Дейнека',
        '(1899-1969) Александр Дейнека - один из самых известных советских художников, создатель монументальных полотен с не менее монументальными героями - простыми советскими людьми, спортсменами,  солдатами, моряками. В этом году картина Александра Дейнеки «За занавеской» была продана на лондонском аукционе MacDougall''s за 2 миллиона 248 тысяч фунтов.'),
       ('Николай Рерих', '(1874-1947)'),
       ('Симон Ушаков', '(1626-1686)'),
       ('Гурий Никитин', '(1620-1691)'),
       ('Леон Бакст', '(1866-1924)'),
       ('Илья Глазунов', '(г.р. 1930)'),
       ('Петров-Водкин', '(1878-1939)'),
       ('Илья Кабаков', '(г.р.1933)'),
       ('Василий Кандинский', '(1855-1944)'),
       ('Павел Филонов', '(1883-1941)');