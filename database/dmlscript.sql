USE [personsdb]
GO
SET IDENTITY_INSERT [dbo].[addresses] ON 

INSERT [dbo].[addresses] ([id], [country_code], [city], [postal_code], [street]) VALUES (1, N'HU', N'Budapest', N'1015', N'Batthyany u. 18.')
INSERT [dbo].[addresses] ([id], [country_code], [city], [postal_code], [street]) VALUES (2, N'HU', N'Budapest', N'1015', N'Batthyany u. 18.')
INSERT [dbo].[addresses] ([id], [country_code], [city], [postal_code], [street]) VALUES (3, N'HU', N'Budapest', N'1015', N'Batthyany u. 18.')
INSERT [dbo].[addresses] ([id], [country_code], [city], [postal_code], [street]) VALUES (7, N'HU', N'Szeged', N'6720', N'Kárász utca 3.')
INSERT [dbo].[addresses] ([id], [country_code], [city], [postal_code], [street]) VALUES (8, N'HU', N'Kecskemét', N'6000', N'Kodály u. 120.')
SET IDENTITY_INSERT [dbo].[addresses] OFF
GO
SET IDENTITY_INSERT [dbo].[contacts] ON 

INSERT [dbo].[contacts] ([id], [address_id], [phone_number], [email]) VALUES (1, 1, N'+30309995566', N'david@email.com')
INSERT [dbo].[contacts] ([id], [address_id], [phone_number], [email]) VALUES (2, 1, N'+30301178921', N'janos@email.com')
INSERT [dbo].[contacts] ([id], [address_id], [phone_number], [email]) VALUES (3, 3, N'+303011783344', N'bela11@email.com')
INSERT [dbo].[contacts] ([id], [address_id], [phone_number], [email]) VALUES (4, 2, N'+303011783344', N'bela2@email.com')
SET IDENTITY_INSERT [dbo].[contacts] OFF
GO
SET IDENTITY_INSERT [dbo].[persons] ON 

INSERT [dbo].[persons] ([id], [name], [identity_card_no], [permanent_address_id], [temporary_address_id]) VALUES (1, N'Teszt Elek', N'123456XA', 2, 3)
INSERT [dbo].[persons] ([id], [name], [identity_card_no], [permanent_address_id], [temporary_address_id]) VALUES (2, N'Minta Melinda', N'223344XA', 3, NULL)
INSERT [dbo].[persons] ([id], [name], [identity_card_no], [permanent_address_id], [temporary_address_id]) VALUES (3, N'Próba János', N'990099TT', 3, 1)
INSERT [dbo].[persons] ([id], [name], [identity_card_no], [permanent_address_id], [temporary_address_id]) VALUES (5, N'Minta Péter', N'131313BA', 1, 2)
INSERT [dbo].[persons] ([id], [name], [identity_card_no], [permanent_address_id], [temporary_address_id]) VALUES (7, N'Hiteles Andrea', N'225533CE', 2, NULL)
SET IDENTITY_INSERT [dbo].[persons] OFF
GO
